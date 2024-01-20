# frozen_string_literal: true

require 'webmock/minitest'

module Testing
  class HttpStubs
    def self.register(method, endpoint, request, response)
      WebMock.stub_request(method, "localhost#{endpoint}").with(request).to_return(response)
    end
  end
end
