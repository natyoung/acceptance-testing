import {render, waitFor} from '@testing-library/react'
import Certification from '@/app/certification/page'
jest.mock('next/image')

it('renders certification page unchanged', async () => {
  const {container} = await waitFor(() => {
    return render(<Certification/>);
  })

  expect(container).toMatchSnapshot()
})
