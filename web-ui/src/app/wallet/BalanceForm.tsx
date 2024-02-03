"use client"

import {FormEvent, useState} from "react";
import WalletService from "@/app/lib/WalletService";
import Error from "@/app/Error";

const host: string = process.env.API_BASE_URI || 'http://0.0.0.0:8080';

export default function BalanceForm(props: any) {
  const updateBalance = props.updateBalance
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const [error, setError] = useState<string>('')

  async function onSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    setIsLoading(true)
    setError('')
    const service = new WalletService(host)
    const formData = new FormData(event.currentTarget)
    const balance = formData.get('amount')

    try {
      const response = await service.addFunds(
        'ea4ceefe-cfe6-49e8-a36d-1a889c780bb4', Number(balance))
      if (response.status !== 200) {
        setError(response.statusText)
      }
      await updateBalance(response.data.balance)
    } catch (error) {
      setError('I am error.')
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <form onSubmit={onSubmit} className="bg-dark shadow-md rounded px-8 pt-6 pb-8 mb-4">
      <div className="mb-4">
        <label className="block text-blue-500  text-sm font-bold mb-2" htmlFor="amount">
          Story Points delivered to the dev environment:
        </label>
        <input
          className="shadow appearance-none border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          type="text" name="amount" data-testid="amount"
        />
      </div>
      <div className="items-center justify-between mb-4">
        <button
          className="bg-blue-800 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
          type="submit" disabled={isLoading} data-testid="submit-amount">
          {isLoading ? 'Loading...' : 'Add Points'}
        </button>
      </div>
      {(error && error.length > 0) && (
        <Error message={error}/>
      )}
    </form>
  )
}
