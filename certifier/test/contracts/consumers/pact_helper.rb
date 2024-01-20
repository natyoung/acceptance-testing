require 'pact/provider/rspec'

Pact.service_provider "web-api" do
  app { Rack::Builder.parse_file(Pact.configuration.config_ru_path) }

  honours_pact_with 'web-client' do
    pact_uri "#{ENV.fetch('PACT_FOLDER', nil)}/web-api-certifier.json"
  end
end

Pact.provider_states_for "web-api" do
  tear_down do
    Certification.delete_all
  end

  provider_state "There is a certifications endpoint" do
    set_up do
    end
  end

  provider_state "There is a certification price" do
    set_up do
    end
  end
end
