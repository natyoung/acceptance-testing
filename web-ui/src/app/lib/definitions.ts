import {inter} from "@/app/ui/fonts";

export class Wallet {
  id: string;
  balance: number;

  constructor(id: string, balance: number) {
    this.id = id;
    this.balance = balance;
  }
}

export class Certificate {
  id: string = '';
  name: string = '';
  date: string = '';
  award: string = '';

  constructor(id: string, name: string, date: string) {
    this.id = id;
    this.name = name;
    this.date = this.formatDate(date);
    this.award = this.randomAward()
  }

  formatDate = (s: string) => {
    const date = new Date(s);
    return date.toDateString();
  }

  randomAward() {
    const awards = ['🎖️', '🏅', '🤡', '🤦‍', '🏆', '🫃']
    return awards[Math.floor(Math.random() * awards.length)];
  }
}
