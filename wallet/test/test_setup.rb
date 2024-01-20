# frozen_string_literal: true

require 'minitest/autorun'
require 'minitest/unit'
require 'mocha/minitest'
require 'faker'
require 'rack/test'
require 'rack'
require 'json'
require_relative 'stubs/mock_http_client'
require_relative 'stubs/http_stubs'
require_relative 'stubs/reader'

OUTER_APP = Rack::Builder.parse_file('config.ru').first

def app
  OUTER_APP
end
