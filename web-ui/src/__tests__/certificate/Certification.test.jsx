import '@testing-library/jest-dom'
import {render, screen, waitFor} from '@testing-library/react'
import Certification from '@/app/certification/Certification'
import {Certificate} from "@/app/lib/definitions";
import {faker} from "@faker-js/faker";

describe('<Certification/>', () => {
  it('renders the certificate fields', async () => {
    const id = faker.string.uuid();
    const name = faker.person.fullName();
    const date = faker.date.anytime().toDateString();
    const certificate = new Certificate(id, name, date);

    await waitFor(() => {
      render(<Certification certificate={certificate}/>);
    })

    expect(screen.getByTestId('certificate-id').textContent).toEqual(id)
    expect(screen.getByTestId('certified-name').textContent).toEqual(name)
    expect(screen.getByTestId('date-certified').textContent).toEqual(date)
  });
});
