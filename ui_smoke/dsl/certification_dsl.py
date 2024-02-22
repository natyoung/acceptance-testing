from playwright.sync_api import Locator

from drivers.certification_driver import CertificationDriver


class CertificationDSL:
    def __init__(self, driver: CertificationDriver):
        self.certification = driver

    def visit(self) -> None:
        self.certification.visit()

    def title(self) -> Locator:
        return self.certification.title()

    def price(self) -> Locator:
        return self.certification.price()

    def buy(self, name: str) -> None:
        self.certification.buy(name)

    def certified_name(self) -> Locator:
        return self.certification.certified_name()

    def certified_date(self) -> Locator:
        return self.certification.certified_date()

    def close(self) -> None:
        self.certification.close()
