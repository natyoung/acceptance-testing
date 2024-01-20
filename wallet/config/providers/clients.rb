# frozen_string_literal: true

Application.register_provider(:clients) do
  start do
    if ENV.fetch('APP_ENV') == 'test'
      Testing::MockHttpClient.register_responses
      Testing::MockHttpClient.base_uri('localhost')
      register('clients.payment', Clients::PaymentProcessor.new(Testing::MockHttpClient))
    else
      Clients::Http.base_uri('http://localhost:9090')
      register('clients.payment', Clients::PaymentProcessor.new(Clients::Http))
    end
  end
end
