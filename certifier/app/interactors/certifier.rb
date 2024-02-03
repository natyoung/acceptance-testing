require 'interactor'
require 'securerandom'

class Certifier
  include Interactor

  def call
    begin
      cert = Certification.create(
        user_id: context.userId,
        name: context.name,
        certification_id: SecureRandom.uuid,
        date_certified: DateTime.new.to_date
      )

      if cert
        context.certification_id = cert.certification_id
        context.certified_name = cert.name
        context.date = cert.date_certified
        context.userId = cert.user_id
      else
        raise StandardError.new("Error creating certificate.")
      end
    rescue StandardError => e
      context.fail!({ message: e.message })
    end
  end
end
