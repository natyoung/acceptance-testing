class CreateCertifications < ActiveRecord::Migration[7.1]
  def change
    create_table :certifications do |t|
      t.string :certification_id
      t.string :name
      t.string :user_id
      t.datetime :date_certified
      t.timestamps
    end
  end
end
