import {render, waitFor} from '@testing-library/react'
import Wallet from '../../app/wallet/page'
import WalletService from '@/app/lib/WalletService'
jest.mock('next/image')

beforeEach(() => {
  jest.spyOn(WalletService.prototype, 'getWallet')
    .mockImplementation(() => Promise.resolve(
      {id: '', balance: 1}
    ));
});

it('renders wallet page unchanged', async () => {
  const {container} = await waitFor(() => {
    return render(<Wallet/>);
  })

  expect(container).toMatchSnapshot()
})
