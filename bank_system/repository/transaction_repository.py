# database/db_connection.py
import psycopg2
from psycopg2 import sql

class DatabaseConnection:
    def __init__(self, dbname, user, password, host="localhost"):
        self.dbname = dbname
        self.user = user
        self.password = password
        self.host = host

    def get_connection(self):
        """Returns a connection to the database."""
        return psycopg2.connect(
            dbname=self.dbname,
            user=self.user,
            password=self.password,
            host=self.host
        )

    # database/db_connection.py
    def execute_query(self, query, params=None, fetch=False):
        """
        Executes a query and optionally fetches results.
        :param query: The SQL query to execute.
        :param params: Parameters for the query.
        :param fetch: Whether to fetch results (for SELECT queries).
        :return: Fetched results if fetch=True, otherwise None.
        """
        conn = self.get_connection()
        cur = conn.cursor()
        try:
            cur.execute(query, params)
            conn.commit()
            if fetch:
                return cur.fetchall() or []  # Return an empty list if no rows are found
        except Exception as e:
            conn.rollback()
            raise e
        finally:
            cur.close()
            conn.close()