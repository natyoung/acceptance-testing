import unittest
import re

from playwright.sync_api import expect

from drivers.web.certification_page import CertificationPage
from dsl.certification_dsl import CertificationDSL


class TestCertification(unittest.TestCase):

    def setUp(self):
        driver = CertificationPage()  # TODO: driver type config
        self.certification = CertificationDSL(driver)

    def tearDown(self):
        self.certification.close()

    def test_access_certification_page(self):
        self.certification.visit()
        expect(self.certification.title()).to_contain_text("Certification")

    def test_view_certification_price(self):
        self.certification.visit()
        expect(self.certification.price()).to_contain_text("100")
        # expect(self.certification.price()).to_contain_text(re.compile(r"[0-9]+"))

    def test_buy_a_certification(self):
        self.certification.visit()
        name = "scrumfall master name"
        date = "Sat Jan 01 1876"
        self.certification.buy(name)
        expect(self.certification.certified_name()).to_contain_text(name)
        expect(self.certification.certified_date()).to_contain_text(date)
