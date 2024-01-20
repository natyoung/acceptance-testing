class Certification < ApplicationRecord
  PRICE = 100

  validates :name, presence: true
  validates :user_id, presence: true
  validates :date_certified, presence: true
  before_create :maybe_assign_id

  def maybe_assign_id
    self.certification_id = SecureRandom.uuid if self.certification_id.blank?
  end
end
