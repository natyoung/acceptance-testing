import '@testing-library/jest-dom'
import {faker} from "@faker-js/faker";
import {fireEvent, render, screen, waitFor} from '@testing-library/react'
import Certification from '@/app/certification/page'
import CertificationService from "@/app/lib/CertificationService";

describe('<Certification/>', () => {
  it('initially renders the form', async () => {
    await waitFor(() => {
      render(<Certification/>);
    })

    const buyButton = screen.getByTestId('buy')
    expect(buyButton).toBeInTheDocument()
  });

  it('buy success renders the certificate', async () => {
    const certificateResponse = {
      id: faker.string.uuid(),
      userId: faker.string.uuid(),
      name: faker.person.fullName(),
      date: faker.date.anytime().toDateString(),
    }

    const serviceMock = jest.spyOn(CertificationService.prototype, 'buy')
      .mockImplementation(() => Promise.resolve(
        {
          data: certificateResponse,
          status: 201
        }
      ));

    render(<Certification/>);

    await waitFor(() => {
      fireEvent.click(screen.getByTestId('buy'))
    })

    expect(serviceMock).toHaveBeenCalledTimes(1);
    expect(screen.getByTestId('certificate')).toBeInTheDocument()
    expect(screen.getByTestId('certificate-id').textContent).toEqual(certificateResponse.id);
    expect(screen.getByTestId('certified-name').textContent).toEqual(certificateResponse.name);
    expect(screen.getByTestId('date-certified').textContent).toEqual(certificateResponse.date);
  });
});
