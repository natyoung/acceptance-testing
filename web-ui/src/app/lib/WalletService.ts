import {Wallet} from "@/app/lib/definitions";
import axios, {AxiosResponse} from "axios";

export default class WalletService {
  private host: string;

  constructor(host: string) {
    this.host = host;
  }

  async getWallet(userId: string): Promise<AxiosResponse> {
    return axios.get<Wallet>(`${this.host}/wallets/${userId}`)
      .then(res => res)
  }

  async addFunds(userId: string, amount: number): Promise<AxiosResponse> {
    return axios.patch<Wallet>(`${this.host}/wallets/${userId}`, {
      amount: amount
    })
      .then(res => res)
  }
}
