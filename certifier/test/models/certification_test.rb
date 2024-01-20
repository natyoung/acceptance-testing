require "test_helper"
require 'faker'

class CertificationTest < ActiveSupport::TestCase
  test 'generates certification id once' do
    certification = Certification.create!(
      user_id: Faker::Internet.uuid,
      name: Faker::Name.name,
      date_certified: Date.new
    )
    id = certification.certification_id
    assert_not_empty id

    updated = Certification.find(certification.id)
    updated.name = Faker::Name.name
    updated.save!

    expected = Certification.find(certification.id)
    assert_equal expected.certification_id, id
  end

  test "should not save without a name" do
    certification = Certification.create(
      user_id: Faker::Internet.uuid,
      date_certified: Date.new
    )
    exception = assert_raise do
      certification.save!
    end
    assert_equal("Validation failed: Name can't be blank", exception.message)
  end

  test "should not save without a user_id" do
    certification = Certification.create(
      date_certified: DateTime.new.to_date,
      name: Faker::Hipster.name
    )
    exception = assert_raise do
      certification.save!
    end
    assert_equal("Validation failed: User can't be blank", exception.message)
  end

  test "should not save without a date_certified" do
    certification = Certification.create(
      user_id: Faker::Internet.uuid,
      name: Faker::Hipster.name
    )
    exception = assert_raise do
      certification.save!
    end
    assert_equal("Validation failed: Date certified can't be blank", exception.message)
  end
end
