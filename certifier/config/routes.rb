Rails.application.routes.draw do
  resources :certifications do
    get 'price', :on => :collection
  end

  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Reveal health status on /up that returns 200 if the app boots with no exceptions, otherwise 500.
  # Can be used by load balancers and uptime monitors to verify that the app is live.
  get "up" => "rails/health#show", as: :rails_health_check
  get "/404" => "application#not_found"
  get "/500" => "application#exception"

  # Defines the root path route ("/")
  # root "posts#index"
end
