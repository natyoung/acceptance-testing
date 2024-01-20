import '@testing-library/jest-dom'
import {faker} from "@faker-js/faker";
import {fireEvent, render, screen, waitFor} from '@testing-library/react'
import CertificationService from "@/app/lib/CertificationService";
import CertificationForm from "@/app/certification/CertificationForm";

afterEach(() => {
  jest.clearAllMocks();
});

describe('<CertificationForm />', () => {
  it('renders', async () => {
    await waitFor(() => {
      render(<CertificationForm setCertificate={() => {
      }}/>);
    })

    const buyButton = screen.getByTestId('buy')
    expect(buyButton).toBeInTheDocument()
  });

  it('shows an error message for an error status', async () => {
    const message = faker.lorem.sentence();
    jest.spyOn(CertificationService.prototype, 'buy')
      .mockImplementation(() => Promise.resolve(
        {data: {error: message}, status: 500}
      ));

    await waitFor(() => {
      render(<CertificationForm/>);
      fireEvent.click(screen.getByTestId('buy'))
    })

    const error = screen.getByTestId('error')
    expect(error.textContent).toEqual(message);
  });

  it('shows the price', async () => {
    const amount = String(faker.number.int() + 1)
    jest.spyOn(CertificationService.prototype, 'getPrice')
      .mockImplementation(() => Promise.resolve(
        {data: {price: amount}}
      ));

    await waitFor(() => {
      render(<CertificationForm/>);
    })

    const price = screen.getByTestId('certification-price').textContent
    expect(amount).toEqual(price)
  });
});
