# frozen_string_literal: true

require 'rom-repository'

module Repos
  class Wallet < ROM::Repository[:wallets]
    commands :create, update: :by_pk

    def by_user_id(user_id)
      wallets.where(user_id:).first
    end

    def delete_all
      wallets.delete
    end
  end
end
