class ApplicationController < ActionController::API
  def not_found
    :status => 404
  end

  def exception
    :status => 500
  end
end
