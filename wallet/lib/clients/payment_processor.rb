# frozen_string_literal: true

require 'uri'
require 'json'

module Clients
  class PaymentProcessorClientError < StandardError; end

  class PaymentProcessor
    def initialize(http_client)
      @http_client = http_client
    end

    def pay(user_id, amount)
      uri = "/v1/payments/#{user_id}"
      response = @http_client.post(uri, headers: json_headers,
                                        body: {
                                          amount:
                                        }.to_json)
      return response.body if response.success?

      raise PaymentProcessorClientError, response.code
    end

    private

    def json_headers
      {
        Accept: 'application/json',
        'Content-Type' => 'application/json',
        Authorization: 'Bearer some_access_token'
      }
    end
  end
end
