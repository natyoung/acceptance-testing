# frozen_string_literal: true

require "test_helper"
require 'faker'

describe 'Certify' do
  after :all do
    Certification.delete_all
  end

  it 'handles failure to create certificate' do
    Certification.stub :create, nil do
      context = Certifier.call({
                                 wallet_id: Faker::Internet.uuid,
                                 name: Faker::Name.name,
                               })

      assert context.failure?
      assert_equal context.message, 'Error creating certificate.'
    end
  end

  it 'generates the certificate id' do
    certificate_id = Faker::Internet.uuid
    SecureRandom.stub :uuid, certificate_id do
      context = Certifier.call({
                                 wallet_id: Faker::Internet.uuid,
                                 name: Faker::Name.name,
                               })

      assert context.certification_id == certificate_id
    end
  end

  it 'adds the date_certified' do
    context = Certifier.call({
                               wallet_id: Faker::Internet.uuid,
                               name: Faker::Name.name,
                             })

    assert_kind_of ActiveSupport::TimeWithZone, context.date
  end
end
