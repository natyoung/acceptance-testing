import unittest

from playwright.sync_api import expect

from drivers.web.wallet_page import WalletPage
from dsl.wallet_dsl import WalletDSL


class TestCertificate(unittest.TestCase):

    def setUp(self):
        driver = WalletPage()  # TODO: driver type config
        self.wallet = WalletDSL(driver)

    def tearDown(self):
        self.wallet.close()

    def test_access_wallet_page(self):
        self.wallet.visit()
        expect(self.wallet.title()).to_contain_text("Wallet")

    def test_view_wallet_balance(self):
        self.wallet.visit()
        expect(self.wallet.balance()).to_contain_text("1")

    def test_add_to_wallet_balance(self):
        self.wallet.visit()
        expect(self.wallet.balance()).to_contain_text("100")
        self.wallet.add_points(900)
        expect(self.wallet.balance()).to_contain_text("1000")
