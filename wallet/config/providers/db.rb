# frozen_string_literal: true

Application.register_provider(:db) do
  prepare do
    require 'rom'
    require 'rom-sql'
  end

  start do
    connection = Sequel.connect('sqlite::memory')
    register('db.connection', connection)
    register('db.config', ROM::Configuration.new(:sql, connection))
  end
end
