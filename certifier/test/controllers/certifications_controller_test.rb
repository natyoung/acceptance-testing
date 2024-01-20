require "test_helper"
require "faker"

class CertificationsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @certification = Certification.create!(
      name: Faker::Name.name,
      date_certified: DateTime.new.to_date,
      user_id: Faker::Internet.uuid
    )
  end

  teardown do
    Certification.delete_all
  end

  test "should create a certification" do
    post certifications_path, params: {
      name: Faker::Name.name,
      userId: Faker::Internet.uuid
    }
    assert_response :success
  end

  test "should get the price of a certification" do
    get "#{certifications_path}/price"
    assert_response :success
  end
end
