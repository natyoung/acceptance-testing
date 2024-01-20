from playwright.sync_api import sync_playwright, Locator

from drivers.certification_driver import CertificationDriver


class CertificationPage(CertificationDriver):
    URL: str = 'http://localhost:3000/certification'

    def __init__(self) -> None:
        self.playwright = sync_playwright().start()
        self.browser = self.playwright.chromium.launch()
        self.context = self.browser.new_context()
        self.page = self.context.new_page()

    def visit(self) -> None:
        self.page.goto(f"{self.URL}")

    def title(self) -> Locator:
        return self.page.get_by_role('heading', level=1)

    def price(self) -> Locator:
        return self.page.get_by_test_id('certification-price')

    def buy(self, name: str):
        self.page.get_by_test_id('name').type(name)
        self.page.get_by_test_id('buy').click()

    def certified_name(self) -> Locator:
        return self.page.get_by_test_id('certified-name')

    def certified_date(self) -> Locator:
        return self.page.get_by_test_id('date-certified')

    def close(self):
        self.context.close()
        self.browser.close()
        self.playwright.stop()
