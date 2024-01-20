import '@testing-library/jest-dom'
import {render, screen, waitFor} from '@testing-library/react'
import Wallet from '@/app/wallet/page'
import WalletService from '@/app/lib/WalletService'
import {faker} from "@faker-js/faker";

afterEach(() => {
  jest.clearAllMocks();
});

describe('<Wallet/>', () => {
  it('renders a heading', async () => {
    jest.spyOn(WalletService.prototype, 'getWallet')
      .mockImplementation(() => Promise.resolve(
        { data: {} }
      ));
    await waitFor(() => {
      render(<Wallet/>);
    })
    const heading = screen.getByRole('heading', {level: 1})
    expect(heading).toBeInTheDocument()
  });

  it('renders the balance', async () => {
    const balance = faker.number.int()
    jest.spyOn(WalletService.prototype, 'getWallet')
      .mockImplementation(() => Promise.resolve(
        {data: {id: `${faker.number.int()}`, balance: balance}}
      ));
    await waitFor(() => {
      render(<Wallet/>);
    })
    const actual = screen.getByTestId('balance').textContent
    expect(actual).toEqual(`${balance}`)
  });
})
