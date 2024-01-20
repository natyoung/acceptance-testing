module Relations
  class Wallets < ROM::Relation[:sql]
    schema(:wallets) do
      attribute :id, Types::Integer
      attribute :user_id, Types::String
      attribute :balance, Types::Integer

      primary_key :id
    end
  end
end
