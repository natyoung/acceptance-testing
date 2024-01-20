# frozen_string_literal: true

module Testing
  class StubReader
    def self.read(stub)
      JSON.parse(File.read(File.expand_path("./json/responses/#{stub}.json", __dir__)),
                 symbolize_names: true)
    end
  end
end
