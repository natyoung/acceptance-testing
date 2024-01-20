# frozen_string_literal: true

Application.register_provider(:services) do
  start do
    target.start :clients
    register('services.wallet', Services::Wallet.new)
  end
end
