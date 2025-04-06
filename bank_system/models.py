from sqlalchemy import Column, Integer, String, Numeric, ForeignKey, DateTime
from sqlalchemy.ext.declarative import declarative_base

# Base class for models
Base = declarative_base()

# Define Accounts Table
class Account(Base):
    __tablename__ = "accounts"
    user_id = Column(Integer, primary_key=True, autoincrement=True)
    username = Column(String(50), nullable=False)
    password = Column(String(225), nullable=False)
    balance = Column(Numeric(15, 2), server_default="0.00")

# Define Transactions Table
class Transaction(Base):
    __tablename__ = "transactions"
    transaction_id = Column(Integer, primary_key=True, autoincrement=True)
    sender_id = Column(Integer, ForeignKey("accounts.user_id"), nullable=False)
    receiver_id = Column(Integer, ForeignKey("accounts.user_id"), nullable=False)
    amount = Column(Numeric(15, 2), nullable=False)
    transaction_date = Column(DateTime, server_default="now()")
