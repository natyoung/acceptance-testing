import {Certificate} from "@/app/lib/definitions";
import {FormEvent, useEffect, useState} from "react";
import CertificationService from "@/app/lib/CertificationService";
import Error from "@/app/Error";

interface CertificationFormProps {
  setCertificate: Function;
}

const host: string = process.env.API_BASE_URI || 'http://localhost:8080';

export default function CertificationForm(props: CertificationFormProps) {
  const [error, setError] = useState<string>('')
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const [price, setPrice] = useState<number>(0)

  useEffect(() => {
    const service = new CertificationService(host)
    service.getPrice()
      .then((result) => {
        setPrice(result.data.price)
      })
      .catch(() => {
        setPrice(0);
      })
  }, [])

  async function onSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    setIsLoading(true)
    setError('')

    try {
      const formData = new FormData(event.currentTarget)
      const service = new CertificationService(host)
      const name: FormDataEntryValue = formData.get('name') || '';
      const response = await service.buy(
        'ea4ceefe-cfe6-49e8-a36d-1a889c780bb4', name.toString())

      if (response.status !== 201) {
        setError(response.data.error)
        setIsLoading(false);
        return;
      }
      props.setCertificate(new Certificate(
        response.data.id,
        response.data.name,
        response.data.date
        )
      )
    } catch (error: unknown | Error) {
      if (error instanceof Error) {
        setError((error as Error).message);
      }
      setError('I am error.')
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <form onSubmit={onSubmit} className="bg-dark shadow-md rounded px-8 pt-6 pb-8 mb-4">
      <h1 className="text-4xl">Certification</h1>
      {(price && price > 0) && (
        <label className="block text-gray-400 text-sm font-bold mb-2">
          Now only <span data-testid="certification-price">{price}</span> Story Points!
        </label>
      )}
      <div className="mb-4">
        <label className="block text-blue-500 text-sm font-bold mb-2" htmlFor="name">
          Scrumfall Master&apos;s name:
        </label>
        <input
          className="shadow appearance-none border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          type="text"
          data-testid="name"
          name="name"/>
      </div>
      <button
        className="bg-blue-800 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
        type="submit"
        disabled={isLoading}
        data-testid="buy">
        {isLoading ? 'Loading...' : 'Buy'}
      </button>
      {(error && error.length > 0) && (
        <Error message={error}/>
      )}
    </form>
  )
}
