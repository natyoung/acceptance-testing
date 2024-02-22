from abc import ABC, abstractmethod

from playwright.sync_api import Locator


class CertificationDriver(ABC):
    @abstractmethod
    def visit(self) -> None:
        pass

    @abstractmethod
    def title(self) -> Locator:
        pass

    @abstractmethod
    def price(self) -> Locator:
        pass

    @abstractmethod
    def buy(self, name: str) -> None:
        pass

    @abstractmethod
    def certified_name(self) -> Locator:
        pass

    @abstractmethod
    def certified_date(self) -> Locator:
        pass

    @abstractmethod
    def close(self) -> None:
        pass
