class CertificationsController < ApplicationController
  def create
    result = Certifier.call(certification_params)

    if result.success?
      render json: {
        id: result.certification_id,
        name: result.certified_name,
        date: result.date.to_s,
        userId: result.user_id,
      }, status: :ok
    else
      render json: { error: result.message }, status: :unprocessable_entity
    end
  end

  def price
    render json: {
      price: Certification::PRICE
    }, status: :ok
  end

  private

  def certification_params
    params.permit(:user_id, :name)
  end
end
