"use client"
import {useEffect, useState} from "react";
import WalletService from "@/app/lib/WalletService";
import {Wallet} from "@/app/lib/definitions"
import BalanceForm from "@/app/wallet/BalanceForm";
import Error from "@/app/Error";
import HeroImage from "@/app/HeroImage"

const initialState: Wallet = new Wallet('', 0);
const host: string = process.env.API_BASE_URI || 'http://localhost:8080';
const userId = 'ea4ceefe-cfe6-49e8-a36d-1a889c780bb4'

export default function Page() {
  const [data, setData] = useState(initialState)
  const [isLoading, setLoading] = useState(true)
  const [error, setError] = useState(false)

  useEffect(() => {
    const service = new WalletService(host)
    service.getWallet(userId)
      .then((result) => {
        setLoading(false)
        setData(result.data)
      })
      .catch(() => {
        setLoading(false)
        setError(true)
      })
  }, [])

  const updateBalance = (amount: number) => {
    setData({
      id: data.id,
      balance: amount
    })
  }

  return (
    <main className="flex min-h-screen flex-col items-center justify-between">
      <HeroImage/>
      <div className="mb-32 grid text-center lg:max-w-10xl lg:w-full lg:mb-0 lg:grid-cols-1 lg:text-center">
        <h1 className="text-4xl">Wallet</h1>
        {(isLoading) && (<div>Loading...</div>)}
        {(data) && (
          <div>Balance: <span data-testid="balance">{data.balance}</span></div>
        )}
        <BalanceForm updateBalance={updateBalance}/>
        {(error) && (
          <Error message="Error"/>
        )}
      </div>
    </main>
  )
}
