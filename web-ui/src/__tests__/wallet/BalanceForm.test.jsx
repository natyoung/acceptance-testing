import '@testing-library/jest-dom'
import {fireEvent, render, screen, waitFor} from '@testing-library/react'
import Balance from '@/app/wallet/BalanceForm'
import WalletService from '@/app/lib/WalletService'
import {faker} from '@faker-js/faker';

afterEach(() => {
  jest.clearAllMocks();
});

describe('Balance', () => {
  it('renders the form', async () => {
    await waitFor(() => {
      render(<Balance/>);
    })
    const input = screen.getByTestId('amount')
    expect(input).toBeInTheDocument()
  });

  it('submits the amount', async () => {
    const userId = 'ea4ceefe-cfe6-49e8-a36d-1a889c780bb4'
    const amount = faker.number.int()
    const serviceMock = jest.spyOn(WalletService.prototype, 'addFunds')
      .mockImplementation(() => Promise.resolve(
        {data: {id: userId, balance: amount}, status: 200}
      ));

    await waitFor(() => {
      render(<Balance/>);
      fireEvent.change(screen.getByTestId('amount'), {target: {value: amount}})
      fireEvent.click(screen.getByTestId('submit-amount'))
      expect(serviceMock).toHaveBeenCalledTimes(1);
      expect(serviceMock).toHaveBeenCalledWith(userId, amount);
    })
  });

  it('shows an error message', async () => {
    jest.spyOn(WalletService.prototype, 'addFunds')
      .mockImplementation(() => Promise.resolve(
        {data: {id: `${faker.string.uuid()}`, balance: faker.number.int()}, status: 500}
      ));

    await waitFor(() => {
      render(<Balance/>);
      fireEvent.change(screen.getByTestId('amount'), {target: {value: faker.number.int()}})
      fireEvent.click(screen.getByTestId('submit-amount'))
    })

    const error = screen.getByTestId('error')
    expect(error.textContent.length).toBeGreaterThan(0);
  });
});
