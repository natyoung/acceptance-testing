from playwright.sync_api import sync_playwright, Locator

from drivers.wallet_driver import WalletDriver


class WalletPage(WalletDriver):
    URL: str = 'http://localhost:3000/wallet'

    def __init__(self) -> None:
        self.playwright = sync_playwright().start()
        self.browser = self.playwright.chromium.launch()
        self.context = self.browser.new_context()
        self.page = self.context.new_page()

    def visit(self) -> None:
        self.page.goto(f"{self.URL}")

    def title(self) -> Locator:
        return self.page.get_by_role('heading', level=1)

    def balance(self) -> Locator:
        return self.page.get_by_test_id("balance")

    def add_points(self, amount: int):
        self.page.get_by_test_id('amount').type(str(amount))
        self.page.get_by_test_id('submit-amount').click()

    def close(self):
        self.context.close()
        self.browser.close()
        self.playwright.stop()
