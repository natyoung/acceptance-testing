import '@testing-library/jest-dom'
import {render, screen, waitFor} from '@testing-library/react'
import Error from '@/app/Error'
import {faker} from "@faker-js/faker";

afterEach(() => {
  jest.clearAllMocks();
});

describe('<Error/>', () => {
  it('renders', async () => {
    const message = faker.lorem.sentence()
    await waitFor(() => {
      render(<Error message={message} />);
    })
    const error = screen.getByTestId('error')
    expect(error.textContent).toEqual(message);
  });
});

