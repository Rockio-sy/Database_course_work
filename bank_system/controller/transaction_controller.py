# api/app.py
from flask import Flask, request, jsonify
from repository.transaction_repository import DatabaseConnection
from service.transaction_service import MoneyTransferService

app = Flask(__name__)

# Initialize database connection and service
db_connection = DatabaseConnection(dbname="banko", user="postgres", password="postgres", host="localhost")
money_transfer_service = MoneyTransferService(db_connection)


@app.route("/register", methods=["POST"])
def register():
    data = request.json
    username = data.get("username")
    password = data.get("password")
    if not username or not password:
        return jsonify({"error": "Username and password are required"}), 400

    try:
        money_transfer_service.register_user(username, password)
        return jsonify({"message": "User registered successfully"}), 201
    except Exception as e:
        return jsonify({"error": str(e)}), 400


@app.route("/transfer", methods=["POST"])
def transfer():
    data = request.json
    sender_id = data.get("sender_id")
    receiver_id = data.get("receiver_id")
    amount = data.get("amount")

    # Print the request data
    print("Request Data:", data)

    if not sender_id or not receiver_id or not amount:
        error_message = "Sender ID, receiver ID, and amount are required"
        print("Error:", error_message)
        return jsonify({"error": error_message}), 400

    try:
        money_transfer_service.transfer_money(sender_id, receiver_id, amount)
        print("Transfer successful")
        return jsonify({"message": "Transfer successful"}), 200
    except ValueError as e:
        print("Error:", str(e))
        return jsonify({"error": str(e)}), 400
    except Exception as e:
        print("Error:", str(e))
        return jsonify({"error": str(e)}), 500
# api/app.py
@app.route("/deposit", methods=["POST"])
def deposit():
    data = request.json
    user_id = data.get("user_id")
    amount = data.get("amount")
    if not user_id or not amount:
        return jsonify({"error": "User ID and amount are required"}), 400

    try:
        money_transfer_service.deposit_money(user_id, amount)
        return jsonify({"message": "Deposit successful"}), 200
    except ValueError as e:
        return jsonify({"error": str(e)}), 400
    except Exception as e:
        return jsonify({"error": str(e)}), 500


if __name__ == "__main__":
    app.run(port=5000)  # Use any port except 8080 and 3000, here i adde host 0.0.0.0 and deleted it for Docker, if u wanna use docker, we have to add it
