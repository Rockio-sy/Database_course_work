"""create accounts and transactions tables

Revision ID: 15b970ef49d1
Revises: 
Create Date: 2025-03-15 11:33:27.218121

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '15b970ef49d1'
down_revision: Union[str, None] = None
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None
def upgrade():
    # Create Accounts Table
    op.create_table(
        "accounts",
        sa.Column("user_id", sa.Integer, primary_key=True, autoincrement=True),
        sa.Column("username", sa.String(50), nullable=False),
        sa.Column("password", sa.String(225), nullable=False),
        sa.Column("balance", sa.Numeric(15, 2), server_default="0.00"),
        sa.UniqueConstraint("username", name="accounts_username_key"),
    )

    # Create Transactions Table
    op.create_table(
        "transactions",
        sa.Column("transaction_id", sa.Integer, primary_key=True, autoincrement=True),
        sa.Column("sender_id", sa.Integer, sa.ForeignKey("accounts.user_id"), nullable=False),
        sa.Column("receiver_id", sa.Integer, sa.ForeignKey("accounts.user_id"), nullable=False),
        sa.Column("amount", sa.Numeric(15, 2), nullable=False),
        sa.Column("transaction_date", sa.DateTime, server_default=sa.func.current_timestamp()),
    )

def downgrade():
    # Drop Tables in Reverse Order
    op.drop_table("transactions")
    op.drop_table("accounts")
