import {MatchersV3, PactV3} from '@pact-foundation/pact';
import WalletService from "@/app/lib/WalletService";
import CertificationService from "@/app/lib/CertificationService";

const path = require('path');

const provider = new PactV3({
  dir: path.resolve(process.env.PACT_FOLDER),
  consumer: 'web-ui',
  provider: 'web-api',
});

const userIdWithBalance100 = 'ea4ceefe-cfe6-49e8-a36d-1a889c780bb4'
const nonExistentWalletId = '0'

const HTTP_OK = 200;
const HTTP_CREATED = 201;
const HTTP_NOT_FOUND = 404;

describe('GET /wallets', () => {
  it('returns status OK and the wallet', async () => {
    const expected = {id: userIdWithBalance100, balance: 100};
    const EXPECTED_BODY = MatchersV3.like(expected);

    provider
      .given('I have a user_id and wallet',
        {userIdWithBalance100: userIdWithBalance100}) // TODO: is there any point of this because of the BFF & stub server?
      .uponReceiving('a query by user_id')
      .withRequest({
        method: 'GET',
        path: `/wallets/${userIdWithBalance100}`,
      })
      .willRespondWith({
        status: HTTP_OK,
        headers: {
          'Content-Type': 'application/json',
        },
        body: EXPECTED_BODY,
      });

    return provider.executeTest(async (mockserver) => {
      const walletService = new WalletService(mockserver.url);
      const response = await walletService.getWallet(userIdWithBalance100)

      expect(response.data).toMatchObject(expected);
      expect(response.status).toEqual(HTTP_OK);
    });
  });
});

describe('PATCH /wallets', () => {
  it('returns status 200', async () => {
    const amount = 900;
    const expected = {balance: 1000};
    const EXPECTED_BODY = MatchersV3.like(expected);

    provider
      .given('I have a user_id and wallet',
        {walletIdWithBalance100: userIdWithBalance100})
      .uponReceiving('a balance update')
      .withRequest({
        method: 'PATCH',
        path: `/wallets/${userIdWithBalance100}`,
        body: {amount: amount}
      })
      .willRespondWith({
        status: HTTP_OK,
        headers: {
          'Content-Type': 'application/json',
        },
        body: EXPECTED_BODY,
      });

    return provider.executeTest(async (mockserver) => {
      const walletService = new WalletService(mockserver.url);
      const response = await walletService.addFunds(userIdWithBalance100, amount)

      expect(response.data).toMatchObject(expected);
      expect(response.status).toEqual(HTTP_OK);
    });
  });

  it('returns status 404 for non-existent wallet id update', async () => {
    const amount = 100;

    provider
      .given('I have an non-existent user_id', {nonExistentWalletId: nonExistentWalletId})
      .uponReceiving('a payment request')
      .withRequest({
        method: 'PATCH',
        path: `/wallets/${nonExistentWalletId}`,
        body: {amount: amount}
      })
      .willRespondWith({
        status: HTTP_NOT_FOUND,
      });

    return provider.executeTest(async (mockserver) => {
      const walletService = new WalletService(mockserver.url);
      await expect(walletService.addFunds(nonExistentWalletId, amount))
        .rejects.toThrow('Request failed with status code 404');
    });
  });
});

describe('POST /certifications', () => {
  it('returns status 201', async () => {
    const certificationId = "528db56a-6f84-481d-986f-069e58ea0d4a";
    const certifiedName = 'scrumfall master name';
    const dateCertified = '01/01/1876';
    const expected = {
      certificateId: certificationId,
      certifiedName: certifiedName,
      dateCertified: dateCertified
    };
    const EXPECTED_BODY = MatchersV3.like(expected);

    provider
      .given('There is a certifications endpoint',
        {walletIdWithBalance100: userIdWithBalance100})
      .uponReceiving('a certification request')
      .withRequest({
        method: 'POST',
        path: `/certifications`,
        body: {
          user_id: userIdWithBalance100,
          name: certifiedName,
        }
      })
      .willRespondWith({
        status: HTTP_CREATED,
        headers: {'Content-Type': 'application/json'},
        body: EXPECTED_BODY,
      });

    return provider.executeTest(async (mockserver) => {
      const service = new CertificationService(mockserver.url);
      const response = await service.buy(userIdWithBalance100, certifiedName)

      expect(response.status).toEqual(HTTP_CREATED);
      expect(response.data).toMatchObject(expected);
    });
  });

  describe('GET /certifications/price', () => {
    it('returns status OK and the price', async () => {
      const expected = {price: 100};
      const EXPECTED_BODY = MatchersV3.like(expected);

      provider
        .given("There is a certification price")
        .uponReceiving('a query for the price')
        .withRequest({
          method: 'GET',
          path: `/certifications/price`,
        })
        .willRespondWith({
          status: HTTP_OK,
          headers: {
            'Content-Type': 'application/json',
          },
          body: EXPECTED_BODY,
        });

      return provider.executeTest(async (mockserver) => {
        const service = new CertificationService(mockserver.url);
        const response = await service.getPrice()

        expect(response.data).toMatchObject(expected);
        expect(response.status).toEqual(HTTP_OK);
      });
    });
  });
});
