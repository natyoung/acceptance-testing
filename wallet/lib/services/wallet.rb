# frozen_string_literal: true

module Services
  class Wallet
    def initialize
      @repo = Application['repos.wallet']
      @payment_client = Application['clients.payment']
    end

    def find_or_create(user_id)
      wallet = @repo.by_user_id(user_id)
      wallet = @repo.create({ user_id:, balance: 0 }) if wallet.nil?
      wallet
    end

    def by_user_id(user_id)
      @repo.by_user_id(user_id)
    end

    def pay(user_id, amount)
      wallet = by_user_id(user_id)
      return nil if wallet.nil?

      response = @payment_client.pay(user_id, amount) # TODO: error checking
      return nil if response.nil?

      new_balance = wallet.balance + amount.to_i
      @repo.update(wallet.id, balance: new_balance)
    end
  end
end
