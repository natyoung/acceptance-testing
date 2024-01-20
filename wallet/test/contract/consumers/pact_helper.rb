# frozen_string_literal: true

require_relative '../../test_setup'
require 'pact/provider/rspec'

Pact.service_provider 'wallet' do
  honours_pact_with 'web-api' do
    pact_uri "#{ENV.fetch('PACT_FOLDER', nil)}/web-api-wallet.json"
  end
end

Pact.provider_states_for 'web-api' do
  provider_state 'I have a user_id and wallet' do
    set_up do
      repo = Application['repos.wallet']
      repo.create({ user_id: 'ea4ceefe-cfe6-49e8-a36d-1a889c780bb4', balance: 100 })
    end
  end

  provider_state 'I have an non-existent user_id' do
    set_up do
      repo = Application['repos.wallet']
      repo.delete_all
    end
  end
end
