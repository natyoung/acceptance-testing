# frozen_string_literal: true

require_relative '../test_setup'

class MainRoutesTest < MiniTest::Test
  include Rack::Test::Methods

  def setup
    repo = Application['repos.wallet']
    @payment_client = Application['clients.payment']
    @wallet = repo.create({ user_id: Faker::Internet.uuid, balance: Faker::Number.number })
    @wallet2 = repo.create({ user_id: Faker::Internet.uuid, balance: Faker::Number.number })
  end

  def test_get_public_status_check
    get '/status_check'
    assert_equal 200, last_response.status
  end

  def test_get_wallet
    get "/wallets/#{@wallet2.user_id}"
    assert_equal 200, last_response.status
    body = JSON.parse(last_response.body)
    assert_equal @wallet2.user_id, body['userId']
    assert_equal @wallet2.balance, body['balance']
  end

  def test_get_wallet_creates_new
    user_id = Faker::Hipster.word
    get "/wallets/#{user_id}"
    assert_equal 200, last_response.status
    body = JSON.parse(last_response.body)
    assert_equal user_id, body['userId']
    assert_equal 0, body['balance']
  end

  def test_pay
    @payment_client.expects(:pay).returns(
      Testing::StubReader.read('external_api/post_payment_response_body').to_json
    )

    amount = Faker::Number.number
    patch "/wallets/#{@wallet.user_id}", { amount: }.to_json
    assert_equal 200, last_response.status
    body = JSON.parse(last_response.body)
    assert_equal @wallet.user_id, body['userId']
    assert_equal @wallet.balance + amount, body['balance']
  end
end
