from abc import ABC, abstractmethod

from playwright.sync_api import Locator


class WalletDriver(ABC):
    @abstractmethod
    def visit(self) -> None:
        pass

    @abstractmethod
    def title(self) -> Locator:
        pass

    @abstractmethod
    def balance(self) -> Locator:
        pass

    @abstractmethod
    def add_points(self, amount: int):
        pass

    @abstractmethod
    def close(self) -> None:
        pass
