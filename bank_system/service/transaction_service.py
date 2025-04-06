# services/money_transfer_service.py
import bcrypt
from psycopg2 import sql

from repository.transaction_repository import DatabaseConnection


class MoneyTransferService:
    def __init__(self, db_connection):
        self.db = db_connection

    def hash_password(self, password):
        """Hashes a password using bcrypt."""
        return bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())

    def register_user(self, username, password):
        """Registers a new user."""
        hashed_password = self.hash_password(password)
        query = "INSERT INTO accounts (username, password) VALUES (%s, %s)"
        self.db.execute_query(query, (username, hashed_password.decode('utf-8')))

    # services/money_transfer_service.py
    def transfer_money(self, sender_id, receiver_id, amount):
        """Transfers money from sender to receiver."""
        if amount <= 0:
            raise ValueError("Amount must be greater than 0.")

        try:
            # Check if sender exists
            sender_query = "SELECT balance FROM accounts WHERE user_id = %s FOR UPDATE"
            sender_result = self.db.execute_query(sender_query, (sender_id,), fetch=True)
            if not sender_result:
                raise ValueError("Sender not found.")

            sender_balance = sender_result[0][0]
            if sender_balance < amount:
                raise ValueError("Insufficient funds.")

            # Check if receiver exists
            receiver_query = "SELECT user_id FROM accounts WHERE user_id = %s"
            receiver_result = self.db.execute_query(receiver_query, (receiver_id,), fetch=True)
            if not receiver_result:
                raise ValueError("Receiver not found.")

            # Deduct from sender
            deduct_query = "UPDATE accounts SET balance = balance - %s WHERE user_id = %s"
            self.db.execute_query(deduct_query, (amount, sender_id))

            # Add to receiver
            add_query = "UPDATE accounts SET balance = balance + %s WHERE user_id = %s"
            self.db.execute_query(add_query, (amount, receiver_id))

            # Log transaction
            log_query = "INSERT INTO transactions (sender_id, receiver_id, amount) VALUES (%s, %s, %s)"
            self.db.execute_query(log_query, (sender_id, receiver_id, amount))

        except Exception as e:
            raise e
    def deposit_money(self, user_id, amount):
        """Deposits money into a user's account."""
        if amount <= 0:
            raise ValueError("Amount must be greater than 0.")

        query = "UPDATE accounts SET balance = balance + %s WHERE user_id = %s"
        self.db.execute_query(query, (amount, user_id))
