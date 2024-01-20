# frozen_string_literal: true

require_relative '../../test_setup'
require_relative '../../stubs/http_stubs'
require_relative '../../stubs/mock_http_client'
require_relative '../../stubs/reader'
require_relative '../../../lib/clients/payment_processor'

module Clients
  class PaymentProcessorClientTest < MiniTest::Test
    def setup
      Testing::MockHttpClient.register_responses
      Testing::MockHttpClient.base_uri('localhost')
      @client = Clients::PaymentProcessor.new(Testing::MockHttpClient)
    end

    def test_pay_returns_body_from_api
      response = @client.pay(Testing::MockHttpClient::USER_1, 900)
      assert_equal(Testing::StubReader.read('external_api/post_payment_response_body').to_json, response)
    end

    def test_pay_failure_raises_error
      assert_raises PaymentProcessorClientError do
        @client.pay(Testing::MockHttpClient::USER_2, 0)
      end
    end
  end
end
