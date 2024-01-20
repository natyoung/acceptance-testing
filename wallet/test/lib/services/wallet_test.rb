# frozen_string_literal: true

require_relative '../../test_setup'
require_relative '../../../lib/services/wallet'
require_relative '../../../config/application'

require 'dry/system/stubs'

class WalletTest < MiniTest::Test
  def setup
    repo = Application['repos.wallet']
    @service = Application['services.wallet']
    @payment_client = Application['clients.payment']
    @wallet = repo.create({ user_id: Faker::Internet.uuid, balance: Faker::Number.number })
  end

  def test_by_user_id
    response = @service.by_user_id(@wallet.user_id)
    assert_equal @wallet.user_id, response.user_id
  end

  def test_by_non_existent_id
    response = @service.by_user_id("#{@wallet.user_id}-#{Faker::Hipster.word}")
    assert_nil response
  end

  def test_find_or_create_non_existing
    user_id = "#{@wallet.user_id}-new"
    response = @service.find_or_create(user_id)
    assert_equal user_id, response.user_id
  end

  def test_find_or_create_existing
    response = @service.find_or_create(@wallet.user_id)
    assert_equal @wallet.user_id, response.user_id
  end

  def test_pay_with_valid_user_id_payment_success
    @payment_client.expects(:pay).returns(
      Testing::StubReader.read('external_api/post_payment_response_body').to_json
    )

    amount = Faker::Number.number
    response = @service.pay(@wallet.user_id, amount.to_s)
    assert_equal amount + @wallet.balance, response.balance
  end

  def test_pay_with_valid_user_id_payment_failed
    @payment_client.expects(:pay).returns(nil) # TODO: error checking

    amount = Faker::Number.number
    response = @service.pay(@wallet.user_id, amount.to_s)
    assert_nil response
  end

  def test_pay_with_invalid_user_id
    amount = Faker::Number.number
    response = @service.pay("#{@wallet.user_id}#{Faker::Hipster.word}", amount.to_s)
    assert_nil response
  end
end
