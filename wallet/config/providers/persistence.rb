# frozen_string_literal: true

Application.register_provider(:persistence) do
  start do
    target.start :db
    config = target['db.config']
    config.register_relation(Relations::Wallets)
    container = ROM.container(config)

    container.gateways[:default].tap do |gateway|
      migration = gateway.migration do
        change do
          create_table :wallets do
            primary_key :id
            string :user_id, null: false
            integer :balance, null: false, default: 0
          end
        end
      end
      migration.apply gateway.connection, :up
    end

    register('container', container)
  end
end
