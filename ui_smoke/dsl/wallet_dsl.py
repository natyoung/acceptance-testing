from playwright.sync_api import Locator

from drivers.wallet_driver import WalletDriver


class WalletDSL:
    def __init__(self, driver: WalletDriver):
        self.wallet = driver

    def visit(self) -> None:
        self.wallet.visit()

    def title(self) -> Locator:
        return self.wallet.title()

    def balance(self) -> Locator:
        return self.wallet.balance()

    def add_points(self, amount: int) -> None:
        return self.wallet.add_points(amount)

    def close(self) -> None:
        self.wallet.close()
