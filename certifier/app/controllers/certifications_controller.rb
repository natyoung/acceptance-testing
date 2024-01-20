class CertificationsController < ApplicationController
  def create
    result = Certifier.call(certification_params)

    if result.success?
      render json: {
        certificateId: result.certification_id,
        certifiedName: result.certified_name,
        dateCertified: result.date,
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
    params.permit(:userId, :name)
  end
end
