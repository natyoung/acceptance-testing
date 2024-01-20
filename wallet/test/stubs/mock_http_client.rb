# frozen_string_literal: true

require 'httparty'

require_relative 'http_stubs'
require_relative 'reader'

module Testing
  # For running tests on 3rd party API stubs
  class MockHttpClient
    include HTTParty

    USER_1 = 'ea4ceefe-cfe6-49e8-a36d-1a889c780bb4'
    USER_2 = '404b9ee6-520e-4b34-a82b-4b8cc329263d'

    def self.register_responses
      WebMock.reset!

      request_header = { Accept: 'application/json',
                         'Content-Type': 'application/json',
                         Authorization: 'Bearer some_access_token' }
      response_header = { 'Content-Type': 'application/json' }

      [{
        method: :post,
        endpoint: "/v1/payments/#{USER_1}",
        request: {
          headers: request_header,
          body: { amount: 900 }.to_json
        },
        response: { status: 202,
                    headers: response_header,
                    body: Testing::StubReader
                      .read('external_api/post_payment_response_body').to_json }
      },
       {
         method: :post,
         endpoint: "/v1/payments/#{USER_2}",
         request: {
           headers: request_header,
           body: { amount: 0 }.to_json
         },
         response: { status: 402,
                     headers: response_header }
       }].each do |s|
        Testing::HttpStubs.register(s[:method], s[:endpoint], s[:request], s[:response])
      end
    end
  end
end
