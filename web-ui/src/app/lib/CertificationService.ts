import axios, {AxiosResponse} from "axios";

export default class CertificationService {
  private host: string;

  constructor(host: string) {
    this.host = host;
  }

  async buy(userId: string, name: string): Promise<AxiosResponse> {
    return axios.post<AxiosResponse>(`${this.host}/certifications`,
      {
        userId: userId,
        name: name
      })
      .then(res => {
        return res;
      })
  }

  async getPrice(): Promise<AxiosResponse> {
    return axios.get<AxiosResponse>(`${this.host}/certifications/price`)
      .then(res => res)
  }
}
