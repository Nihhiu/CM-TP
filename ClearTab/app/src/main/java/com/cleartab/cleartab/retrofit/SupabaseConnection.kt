package com.cleartab.cleartab.retrofit

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

//Create a Supabase Connection
val  supabase  =  createSupabaseClient(
    supabaseUrl  =  "https://zkbeunyzmgumrwtplpkc.supabase.co ",
    supabaseKey  =  "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InprYmV1bnl6bWd1bXJ3dHBscGtjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTk5NTQ0ODksImV4cCI6MjAzNTUzMDQ4OX0.rWStS8EPEpVy4L6J522gzva1hNF-CcAbySxt3i19eho"
) {
    install(Postgrest)
    install(Auth)
}