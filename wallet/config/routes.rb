# frozen_string_literal: true

require 'sinatra/base'
require 'sinatra/json'

require_relative '../lib/services/wallet'

class ApplicationRoutes < Sinatra::Base

  configure do
    enable :cross_origin
  end

  def initialize
    super
    @service = Application['services.wallet']
  end

  get '/' do
    status 418
  end

  get '/status_check' do
    status 200
  end

  get '/wallets/:user_id' do
    wallet = @service.find_or_create(params['user_id'])

    if wallet.nil?
      status 404
    else
      json map_wallet(wallet)
    end
  end

  patch '/wallets/:user_id' do
    amount = JSON.parse(request.body.read)['amount']
    wallet = @service.pay(params['user_id'], amount)

    if wallet.nil?
      status 404
    else
      json map_wallet(wallet)
    end
  end

  private

  def map_wallet(wallet)
    {
      balance: wallet.balance,
      id: wallet.id.to_s,
      userId: wallet.user_id
    }
  end
end
